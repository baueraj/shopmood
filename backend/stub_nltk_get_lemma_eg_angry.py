from nltk.stem import WordNetLemmatizer
import nltk

# Download WordNet data
nltk.download('wordnet')

def get_root_word(word):
    lemmatizer = WordNetLemmatizer()
    lemma = lemmatizer.lemmatize(word, pos="a") # pos="a" for adjective, since "angry" is an adjective
    return lemma

word = "angrily"
root_word = get_root_word(word)

if root_word == "angry":
    print(f"The root word of {word} is 'anger'")
else:
    print(f"The root word of {word} is {root_word}")
