package kz.baribir.birkitap.service.booktracker;

import kz.baribir.birkitap.model.booktracker.entity.Note;
import kz.baribir.birkitap.repository.booktracker.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note create(Note note) {
        noteRepository.save(note);
        return note;
    }

    @Override
    public Note get(String id) {
        Note note = noteRepository.findById(id);
        if (note == null) {
            throw new RuntimeException("note with %s id not found".formatted(note.getId()));
        }

        return note;
    }

    @Override
    public Note update(Note note) {
        noteRepository.save(note);
        return note;
    }

    @Override
    public void delete(String id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> list(Map<String, Object> params) {
        return noteRepository.list(params);
    }
}
