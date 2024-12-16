import fitz  # PyMuPDF

def parse_pdf_to_dict(file_path):
    pages_dict = {}
    pdf_doc = fitz.open(file_path)

    for page_num in range(len(pdf_doc)):
        page = pdf_doc.load_page(page_num)
        text = page.get_text("text")
        pages_dict[page_num + 1] = text  # Store text instead of Page object

    return pages_dict
