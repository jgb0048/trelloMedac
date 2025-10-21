// Persistencia en localStorage
const LS_KEY = "localBoards";

// Utilidades
function readAll() {
  const raw = localStorage.getItem(LS_KEY);
  return raw ? JSON.parse(raw) : { boards: {} };
}
function writeAll(data) {
  localStorage.setItem(LS_KEY, JSON.stringify(data));
}
function uid(prefix = "") {
  // UUID simple compatible con todos los navegadores
  return prefix + Math.random().toString(36).slice(2, 10) + Date.now().toString(36);
}

// API del “backend” local
export const BoardsStore = {
  // LISTAR tableros (por ahora sin owner)
  listBoards() {
    const db = readAll();
    return Object.values(db.boards);
  },

  // CREAR tablero (puedes pasar listas iniciales)
  createBoard({ name, lists = [] }) {
    const db = readAll();
    const id = uid("b_");
    const board = {
      id,
      name,
      createdAt: new Date().toISOString(),
      lists: lists.map((l, i) => ({
        id: uid("l_"),
        name: l.name,
        position: (i + 1) * 100,
        cards: (l.cards || []).map((c, j) => ({
          id: uid("c_"),
          title: c.title,
          description: c.description || "",
          position: (j + 1) * 100,
        })),
      })),
    };
    db.boards[id] = board;
    writeAll(db);
    return board;
  },

  // OBTENER tablero
  getBoard(boardId) {
    const db = readAll();
    return db.boards[boardId] || null;
  },

  // AÑADIR lista
  addList(boardId, name) {
    const db = readAll();
    const board = db.boards[boardId];
    if (!board) return null;
    const maxPos = board.lists.reduce((m, l) => Math.max(m, l.position || 0), 0);
    const list = { id: uid("l_"), name, position: maxPos + 100, cards: [] };
    board.lists.push(list);
    writeAll(db);
    return list;
  },

  // AÑADIR tarjeta
  addCard(boardId, listId, title) {
    const db = readAll();
    const board = db.boards[boardId];
    if (!board) return null;
    const list = board.lists.find(l => l.id === listId);
    if (!list) return null;
    const maxPos = list.cards.reduce((m, c) => Math.max(m, c.position || 0), 0);
    const card = { id: uid("c_"), title, description: "", position: maxPos + 100 };
    list.cards.push(card);
    writeAll(db);
    return card;
  },

  // MOVER tarjeta entre listas y/o reordenar
  moveCard({ boardId, cardId, toListId, beforeCardId }) {
    const db = readAll();
    const board = db.boards[boardId];
    if (!board) return null;

    // 1) sacar la card de su lista actual
    let fromList = null, card = null;
    for (const l of board.lists) {
      const idx = l.cards.findIndex(c => c.id === cardId);
      if (idx >= 0) {
        fromList = l;
        [card] = l.cards.splice(idx, 1);
        break;
      }
    }
    if (!card) return null;

    // 2) insertar en la lista destino en la posición correcta
    const toList = board.lists.find(l => l.id === toListId);
    if (!toList) return null;

    if (!beforeCardId) {
      // al final
      const maxPos = toList.cards.reduce((m, c) => Math.max(m, c.position || 0), 0);
      card.position = maxPos + 100;
      toList.cards.push(card);
    } else {
      const idx = toList.cards.findIndex(c => c.id === beforeCardId);
      const prev = toList.cards[idx - 1];
      const next = toList.cards[idx];
      const prevPos = prev ? prev.position : 0;
      const nextPos = next ? next.position : prevPos + 200;
      card.position = Math.floor((prevPos + nextPos) / 2) || nextPos - 1;
      toList.cards.splice(idx, 0, card);
    }

    // 3) guardar
    writeAll(db);
    return card;
  },
};
