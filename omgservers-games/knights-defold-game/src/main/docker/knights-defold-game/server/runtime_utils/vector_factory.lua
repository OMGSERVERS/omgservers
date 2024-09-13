local vector_factory
vector_factory = {
	create = function(self, x, y)
		return {
			x = x or 0,
			y = y or 0,
			-- Methods
			clone = function(vector_instance)
				return vector_factory:create(vector_instance.x, vector_instance.y)
			end,
			set = function(vector_instance, new_x, new_y)
				vector_instance.x = new_x
				vector_instance.y = new_y
			end,
			add = function(vector_instance, v)
				return vector_factory:create(vector_instance.x + v.x, vector_instance.y + v.y)
			end,
			subtract = function(vector_instance, v)
				return vector_factory:create(vector_instance.x - v.x, vector_instance.y - v.y)
			end,
			multiply = function(vector_instance, k)
				return vector_factory:create(vector_instance.x * k, vector_instance.y * k)
			end,
			length = function(vector_instance)
				return math.sqrt(vector_instance.x * vector_instance.x + vector_instance.y * vector_instance.y)
			end,
			length_sqr = function(vector_instance)
				return vector_instance.x * vector_instance.x + vector_instance.y * vector_instance.y
			end,
			normalize = function(vector_instance)
				local l = vector_instance:length()
				return vector_factory:create(vector_instance.x / l, vector_instance.y / l)
			end,
		}
	end,
}

return vector_factory