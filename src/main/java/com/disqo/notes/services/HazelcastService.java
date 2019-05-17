package com.disqo.notes.services;

import com.disqo.notes.sessions.UserSession;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HazelcastService {
    private final HazelcastInstance hazelcastInstance;
    private IMap<String, UserSession> userSessionIMap;

    public HazelcastService(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
        userSessionIMap = hazelcastInstance.getMap("userSessions");
    }

    public UserSession getSession(String id ) {
        return userSessionIMap.get(id);
    }

    public UserSession createSession(UserSession usuarioSession) {
        String id = usuarioSession.getToken();
        if (id == null ) {
            id = UUID.randomUUID().toString();
            usuarioSession.setToken(id);
        }
        userSessionIMap.put( id, usuarioSession, 2L, TimeUnit.DAYS);

        return usuarioSession;
    }

    public void deleteSession(String id) {
        UserSession userSession = userSessionIMap.get(id);
        if(userSession == null) { return; }
        EntryObject e = new PredicateBuilder().getEntryObject();
        Predicate predicate = e.get("id").equal(userSession.getId());
        userSessionIMap.removeAll(predicate);
    }


}
