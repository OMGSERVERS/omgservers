local collision_factory
collision_factory = {
	-- Methods
	create = function(self, client_1, position_1, distance_1, client_2, position_2, distance_2)
		return {
			client_1 = client_1,
			position_1 = position_1,
			distance_1 = distance_1,
			client_2 = client_2,
			position_2 = position_2,
			distance_2 = distance_2,
		}
	end,
}

return collision_factory