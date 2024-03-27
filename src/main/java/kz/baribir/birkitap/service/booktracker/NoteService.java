package kz.baribir.birkitap.service.booktracker;

import kz.baribir.birkitap.model.booktracker.entity.Note;

import java.util.List;
import java.util.Map;

public interface NoteService {
    Note create(Note note);
    Note get(String id);
    Note update(Note note);
    void delete(String id);
    List<Note> list(Map<String, Object> params);
}
