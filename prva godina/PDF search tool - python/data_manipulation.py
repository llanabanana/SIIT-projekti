import fitz  # PyMuPDF
import pickle
from graph import Graph
from trie import Trie
from parse_pdf import parse_pdf_to_dict

def load_pdf_to_graph_and_trie(pdf_path):
    graph = Graph()
    trie = Trie()

    pages_dict = parse_pdf_to_dict(pdf_path)

    for page_num, page_text in pages_dict.items():
        graph.add_page(page_num, page_text)
        words = page_text.split()
        for word in words:
            trie.insert(word.lower(), page_num)

    return graph, trie, pages_dict

def save_data(graph, trie, pages_dict, filename):
    with open(filename, 'wb') as file:
        pickle.dump((graph, trie, pages_dict), file)

def load_data(filename):
    with open(filename, 'rb') as file:
        return pickle.load(file)
