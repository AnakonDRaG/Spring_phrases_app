package com.SGW.Phrases

import com.SGW.Phrases.models.Author
import com.SGW.Phrases.models.Phrase
import com.SGW.Phrases.repositories.AuthorRepository
import com.SGW.Phrases.repositories.PhraseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataBaseLoader implements CommandLineRunner{
    private final AuthorRepository authorRepository;
    private final PhraseRepository phraseRepository;

    @Autowired
    DataBaseLoader(PhraseRepository phraseRepository, AuthorRepository authorRepository) {
        this.phraseRepository = phraseRepository;
        this.authorRepository = authorRepository;
    }
    @Override
    void run(String... args) throws Exception {

        authorRepository.save(new Author(FirstName: "Unknown"))
        authorRepository.save(new Author(FirstName: "Амелия", LastName: "Эрхарт"))


        phraseRepository.save(new Phrase(
                Title:"Needle In a Haystack",
                Meaning: "Something that is impossible or extremely difficult to find, especially because the area you have to search is too large"))
        phraseRepository.save(new Phrase(
                Title:"Drive Me Nuts",
                Meaning: "To greatly frustrate someone. To drive someone crazy, insane, bonkers, or bananas."
        ))
    }
}
