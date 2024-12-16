import search_functions
from data_manipulation import load_pdf_to_graph_and_trie, save_data, load_data

def main():
    pdf_path = "Data Structures and Algorithms in Python ( PDFDrive ).pdf"
    graph, trie, page_dict = load_pdf_to_graph_and_trie(pdf_path)

    save_data(graph, trie, page_dict, 'data.pkl')
    graph, trie, page_dict = load_data('data.pkl')

    print("=============== SEARCH ENGINE ===============")
    print("If you want to exit press X.")

    while True:
        input_phrase = input("Search: ")
        if input_phrase.upper() == "X":
            print("Goodbye.")
            exit()
        else:
            search_functions.search(trie, page_dict, input_phrase)

if __name__ == "__main__":
    main()
