local events_container = require("server.match_runtime.events_container")
local match_events = require("common.match_events")

local players_container
players_container = {
	players = {},
	-- Methods
	add_player = function(self, client_id, player)
		if self.players[client_id] then
			print(socket.gettime() .. " [PLAYER_CONTAINER] Player was already added, skip operations, client_id=" .. client_id)
			return false
		else
			self.players[client_id] = player
			print(socket.gettime() .. " [PLAYER_CONTAINER] Player was added, client_id=" .. client_id)
			return true
		end
	end,
	get_player = function(self, client_id)
		return self.players[client_id]
	end,
	delete_player = function(self, client_id)
		if self.players[client_id] then
			self.players[client_id] = nil
			print(socket.gettime() .. " [PLAYER_CONTAINER] Player was deleted, client_id=" .. client_id)
			return true
		else
			print(socket.gettime() .. " [PLAYER_CONTAINER] Player was not found to delete, client_id=" .. client_id)
			return false
		end
	end,
	get_client_ids = function(self)
		local self_players = self.players
		local client_ids = {}
		for _, player in pairs(self_players) do
			client_ids[#client_ids + 1] = player.client_id
		end
		return client_ids
	end,
	get_spawned_players = function(self)
		local self_players = self.players
		local active_players = {}
		for client_id, player in pairs(self_players) do
			-- Was spawned
			if player.position then
				active_players[client_id] = player
			end
		end
		return active_players
	end,
	get_alive_players = function(self)
		local self_players = self.players
		local alive_players = {}
		for client_id, player in pairs(self_players) do
			-- Was spawned and still alive
			if player.position and not player.was_killed then
				alive_players[client_id] = player
			end
		end
		return alive_players
	end,
	get_simulation_depth = function(self, simulation_step)
		local max_depth = 0
		local alive_players = self:get_alive_players()
		for _, alive_player in pairs(alive_players) do
			local player_movement = alive_player.movement
			if player_movement then
				local simulation_depth = player_movement:get_simulation_depth(simulation_step)
				if simulation_depth > max_depth then
					max_depth = simulation_depth
				end
			end
		end

		return max_depth
	end,
	finish_movements = function(self)
		local self_players = self.players
		for client_id, player in pairs(self_players) do
			local player_movement = player.movement
			if player_movement then
				local position = player_movement.to_position

				local match_event = match_events:player_moved(client_id, position.x, position.y)
				events_container:add_event(match_event)

				player:finish_movement()
			end
		end
	end,
	get_state = function(self)
		local self_players = self.players
		local dangling_players = {}
		local spawned_players = {}
		for client_id, player in pairs(self_players) do
			local position = player.position
			if position then
				spawned_players[client_id] = {
					nickname = player.nickname,
					x = position.x,
					y = position.y,
				}
			else
				dangling_players[client_id] = {
					nickname = player.nickname,
				}
			end
		end

		return {
			dangling_players = dangling_players,
			spawned_players = spawned_players,
		}
	end,
}

return players_container