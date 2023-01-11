package linkedList
class LinkedList {
  var nodes: List[Node] = List()
  var head: Node = null
  var tail: Node = null

  /** Déplace chaque noeud dans la case précédente
    */
  def shift(): Unit = {
    for (node <- nodes) {
      node.xCoord = node.next.xCoord
      node.yCoord = node.next.yCoord
      if (node.next != tail) {
        node.next = node.next.next
      }
    }
  }

  /** Ajoute un noeud dans la liste
    */
  def addNode(xCoord: Int, yCoord: Int): Unit = {
    // Ajouter un noeud à la fin du serpent
    val newNode = new Node()
    newNode.xCoord = xCoord
    newNode.yCoord = yCoord
    if (tail != null) {
      tail.next = newNode
    } else {
      tail = newNode
      head = newNode
    }
    nodes = newNode :: nodes
  }
}

class Node {
  var xCoord: Int = 0
  var yCoord: Int = 0
  var next: Node = null
}
