local movement_factory = require("server.match_manager.movement_factory")

local player_factory
player_factory = {
	-- Methods
	create = function(self, client_id, nickname)
		return {
			client_id = client_id,
			newly_created = true,
			nickname = nickname,
			in_attack = false,
			position = nil,
			movement = nil,
			was_killed = false,
			-- Methods
			spawn_player = function(player_instance, position, in_attack)
				player_instance.position = position:clone()
				player_instance.in_attack = in_attack
				player_instance.was_killed = false
			end,
			set_movement = function(player_instance, target_position)
				player_instance.movement = movement_factory:create(player_instance.client_id, player_instance.position, target_position)
			end,
			finish_movement = function(player_instance)
				player_instance.in_attack = not player_instance.in_attack
				if player_instance.movement then
					local final_position = player_instance.movement.to_position
					player_instance.position = final_position:clone()
				end
				player_instance.movement = nil
			end,
			interrupt_movement = function(player_instance, final_position)
				if player_instance.movement then
					player_instance.movement = movement_factory:create(player_instance.client_id, player_instance.position, final_position)
				end
			end,
			kill_player = function(player_instance)
				player_instance.was_killed = true
			end,
			reset_player = function(player_instance)
				player_instance.newly_created = false
			end,
		}
	end,
}

return player_factory