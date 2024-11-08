local movement_factory = require("server.match_runtime.movement_factory")

local player
player = {
	-- Methods
	create = function(self, client_id, nickname)
		return {
			client_id = client_id,
			nickname = nickname,
			score = 0,
			position = nil,
			movement = nil,
			was_killed = false,
			-- Methods
			spawn_player = function(instance, position)
				instance.position = position:clone()
				instance.was_killed = false
			end,
			set_movement = function(instance, target_position)
				instance.movement = movement_factory:create(instance.client_id, instance.position, target_position)
			end,
			finish_movement = function(instance)
				if instance.movement then
					local final_position = instance.movement.to_position
					instance.position = final_position:clone()
				end
				instance.movement = nil
			end,
			interrupt_movement = function(instance, final_position)
				if instance.movement then
					instance.movement = movement_factory:create(instance.client_id, instance.position, final_position)
				end
			end,
			kill_player = function(instance)
				instance.was_killed = true
			end,
			increase_score = function(instance)
				instance.score = instance.score + 1
			end,
		}
	end,
}

return player