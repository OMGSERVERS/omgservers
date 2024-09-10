components {
  id: "screens_handler"
  component: "/game/screens_manager/screens_handler.script"
}
embedded_components {
  id: "auth_factory"
  type: "collectionfactory"
  data: "prototype: \"/game/auth_screen/auth_screen.collection\"\n"
  ""
}
embedded_components {
  id: "lobby_factory"
  type: "collectionfactory"
  data: "prototype: \"/game/lobby_screen/lobby_screen.collection\"\n"
  ""
}
embedded_components {
  id: "arena_factory"
  type: "collectionfactory"
  data: "prototype: \"/game/arena_screen/arena_screen.collection\"\n"
  ""
}
