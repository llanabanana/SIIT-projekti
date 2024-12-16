class TrieNode:
    def __init__(self):
        self.children = {}
        self.is_end_of_word = False
        self.pages = []


class Trie:
    def __init__(self):
        self.root = TrieNode()

    def insert(self, word, page):
        node = self.root
        for char in word:
            if char not in node.children:
                node.children[char] = TrieNode()
            node = node.children[char]
        node.is_end_of_word = True
        if page not in node.pages:
            node.pages.append(page)

    def search(self, word):
        node = self.root
        for char in word:
            if char not in node.children:
                return []
            node = node.children[char]
        if node.is_end_of_word:
            return node.pages
        else:
            return []
