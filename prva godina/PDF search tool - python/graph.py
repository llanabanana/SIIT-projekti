class Graph:
    def __init__(self):
        self.nodes = {}

    def add_page(self, page_num, page_text):
        self.nodes[page_num] = page_text
