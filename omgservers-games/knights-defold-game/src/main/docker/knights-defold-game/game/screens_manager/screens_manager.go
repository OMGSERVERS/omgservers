components {
  id: "screens_manager"
  component: "/game/screens_manager/screens_manager.script"
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
  id: "match_factory"
  type: "collectionfactory"
  data: "prototype: \"/game/match_screen/match_screen.collection\"\n"
  ""
}
embedded_components {
  id: "wait_factory"
  type: "collectionfactory"
  data: "prototype: \"/game/wait_screen/wait_screen.collection\"\n"
  ""
}
