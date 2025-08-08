package com.example.main;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryLoginStore {

    private final List<LoginRecord> allLogins = new CopyOnWriteArrayList<>();
    private final Map<String, List<LoginRecord>> byEmail = new ConcurrentHashMap<>();

    public void add(LoginRecord rec) {
        allLogins.add(rec);
        byEmail.computeIfAbsent(rec.getEmail(), k -> new CopyOnWriteArrayList<>()).add(rec);
    }

    public List<LoginRecord> getAll() { return Collections.unmodifiableList(allLogins); }

    public List<LoginRecord> getByEmail(String email) {
        return Collections.unmodifiableList(byEmail.getOrDefault(email, List.of()));
    }
}
