package com.rackspace.Puzzle
import com.rackspace.Puzzle.Trial
import com.rackspace.Puzzle.Scale
import com.rackspace.Puzzle.UserSession

class GameController {
	def userSession = session["user"]
	def scale = new Scale()
	
	/**
	 * Displays the index page explaining the rules
	 */
    def index = {
	}
	
	/**
	 * Does the advancement of the game.
	 */
	def trial = {
		scale.reset()
		if (!userSession) {
			session["user"] = userSession = new UserSession()
		}

		if (params.derp) {
			userSession.trialCount++
		}
			
		for (int i = 0; i < 7; i++) {
			if (params["ball" + i]) {
				userSession.trials[i].isTested = true
				userSession.trials[i].side = params["ball" + i]
				
				if (userSession.trials[i].side == "left") {
					scale.addBallLeft(userSession.trials[i].ball)
				}
				else if  (userSession.trials[i].side == "right") {
					scale.addBallRight(userSession.trials[i].ball)
				}
			}
		}
	}
	
	/**
	 * Shows whether you succeeded or failed
	 */
	def results = {
		if (userSession.highestBall.id == params.ball.toInteger()) {
			render "<a href='reset'><img style='width: 100%; height: 100%' src=${createLinkTo(dir: 'images', file: 'winrar.png')} alt='Grails'/></a>"
		}
		else {
			render "<a href='reset'><img style='width: 100%; height: 100%' src=${createLinkTo(dir: 'images', file: 'epic_fail.jpg')} alt='Grails'/></a>"
		}
	}
	
	/**
	 * Resets the userSession
	 */
	def reset = {
		userSession?.reset()
		scale?.reset()
		redirect(controller: "game", action: "trial")
	}
}
