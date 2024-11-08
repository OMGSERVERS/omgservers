local match_top
match_top = {
	ADD_PLAYER = "add_player",
	DELETE_PLAYER = "delete_player",
	INCREASE_SCORE = "increase_score",
	-- Methods
	add_player = function(self, recipient, client_id, nickname, score)
		msg.post(recipient, match_top.ADD_PLAYER, {
			client_id = client_id,
			nickname = nickname,
			score = score,
		})
	end,
	delete_player = function(self, recipient, client_id)
		msg.post(recipient, match_top.DELETE_PLAYER, {
			client_id = client_id,
		})
	end,
	increase_score = function(self, recipient, client_id)
		msg.post(recipient, match_top.INCREASE_SCORE, {
			client_id = client_id,
		})
	end,
}

return match_top