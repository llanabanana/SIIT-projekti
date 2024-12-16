from collections import defaultdict


def search(trie, page_dict, input_phrase):
    results = {}
    words = input_phrase.split()

    if '"' in input_phrase:
        phrase = input_phrase.strip('"')
        results = search_phrase(page_dict, phrase)
        if results:
            print_results(results, page_dict, input_phrase, phrase_search=True)
        else:
            print("No results found.")
    else:
        if len(words) == 1:
            results = search_word(trie, page_dict, input_phrase.lower())
        else:
            if len(words) == 3 and words[1] == "OR":
                results = search_or(trie, page_dict, words[0::2])
            elif len(words) == 3 and words[1] == "AND":
                results = search_and(trie, page_dict, words[0::2])
            elif len(words) == 3 and words[1] == "NOT":
                results = search_not(trie, page_dict, words[0::2])
            else:
                results = search_words(trie, page_dict, input_phrase.lower())
        if results:
            print_results(results, page_dict, input_phrase)
        else:
            print("No results found.")


def search_word(trie, page_dict, word):
    pages = trie.search(word)
    results = defaultdict(int)
    for page in pages:
        results[page] += 1
    return results


def search_phrase(page_dict, phrase):
    results = {}
    for page_num, text in page_dict.items():
        if phrase in text:
            results[page_num] = text.count(phrase)
    return results


def search_and(trie, page_dict, words):
    results = defaultdict(int)
    sets_of_pages = [set(trie.search(word)) for word in words]
    common_pages = set.intersection(*sets_of_pages)
    for page in common_pages:
        results[page] = sum(text.count(word) for word in words for text in page_dict[page].split())
    return results


def search_or(trie, page_dict, words):
    results = defaultdict(int)
    for word in words:
        pages = trie.search(word)
        for page in pages:
            results[page] += 1
    return results


def search_not(trie, page_dict, words):
    include_word, exclude_word = words
    include_pages = set(trie.search(include_word))
    exclude_pages = set(trie.search(exclude_word))
    results = {page: 1 for page in include_pages if page not in exclude_pages}
    return results


def search_words(trie, page_dict, phrase):
    results = defaultdict(int)
    words = phrase.split()
    for word in words:
        pages = trie.search(word)
        for page in pages:
            results[page] += 1
    return results


def print_results(results, page_dict, input_phrase, phrase_search=False):
    sorted_results = sorted(results.items(), key=lambda x: x[1], reverse=True)
    for rank, (page_num, count) in enumerate(sorted_results, start=1):
        context = get_context(page_dict[page_num], input_phrase, phrase_search)
        print(f"Result {rank} - Page {page_num} (Occurrences: {count})")
        print(context)
        print("-" * 80)


def get_context(text, input_phrase, phrase_search):
    start = max(0, text.lower().find(input_phrase.lower()) - 30)
    end = min(len(text), start + len(input_phrase) + 60)
    context = text[start:end]
    if phrase_search:
        context = context.replace(input_phrase, f"\033[93m{input_phrase}\033[0m")
    else:
        for word in input_phrase.split():
            context = context.replace(word, f"\033[93m{word}\033[0m")
    return context
