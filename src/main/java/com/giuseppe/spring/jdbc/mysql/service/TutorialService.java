package com.giuseppe.spring.jdbc.mysql.service;

import com.giuseppe.spring.jdbc.mysql.model.Tutorial;
import com.giuseppe.spring.jdbc.mysql.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorialService {

    private final TutorialRepository repository;

    public TutorialService(TutorialRepository repository) {
        this.repository =repository;
}
// non dipende dalla reflection per l'iniezione (come accade con @Autowired su campo)
//    @Autowired
//    private TutorialRepository repository;

    // delegazione ai metodi del repository
    // non ho definito TutorialService come un interfaccia (API)
    // in modo di implementare TutorialServiceImpl separatamente
    // per poi aggiornare TutorialController per iniettare l'interfaccia
    public int save(Tutorial tutorial) {
        return repository.save(tutorial);
    }

    public int update(Tutorial tutorial) {
        return repository.update(tutorial);
    }

    public Tutorial findById(Long id) {
        return repository.findById(id);
    }

    public int deleteById(Long id) {
        return repository.deleteById(id);
    }

    public List<Tutorial> findAll() {
        return repository.findAll();
    }

    public List<Tutorial> findByPublished(boolean published) {
        return repository.findByPublished(published);
    }

    public List<Tutorial> findByTitleContaining(String title) {
        return repository.findByTitleContaining(title);
    }

    public int deleteAll() {
        return repository.deleteAll();
    }

    // metodi con supporto per orderBy e limit
    public List<Tutorial> findAll(String sort, String direction, int offset, int limit) {
        return repository.findAll(sort, direction, offset, limit);
    }

    public List<Tutorial> findByPublished(boolean published, String sort, String direction, int offset, int limit) {
        return repository.findByPublished(published, sort, direction, offset, limit);
    }

    public List<Tutorial> findByTitleContaining(String title, String sort, String direction, int offset, int limit) {
        return repository.findByTitleContaining(title, sort, direction, offset, limit);
    }
}
