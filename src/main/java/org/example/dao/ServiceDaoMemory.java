package org.example.dao;

import org.example.entity.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceDaoMemory implements GenericDao<Service> {
    private final List<Service> services = new ArrayList<>();

    public ServiceDaoMemory() {
        services.add(new Service("Servizio in camera", "Consegna cibo e bevande direttamente in camera", 24));
        services.add(new Service("Noleggio attrezzatura da snorkeling", "Affitto giornaliero di maschera, boccaglio e pinne", 10));
    }

    @Override
    public void create(Service entity) {
        services.add(entity);
    }

    @Override
    public Service read(String name) {
        return services.stream()
                .filter(service -> service.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Service entity) {
        Service existing = read(entity.getName());
        if (existing != null) {
            services.remove(existing);
            services.add(entity);
        }
    }

    @Override
    public void delete(String name) {
        services.removeIf(service -> service.getName().equalsIgnoreCase(name));
    }

    // Nuovo metodo readAll
    public List<Service> readAll() {
        return new ArrayList<>(services); // Restituisce una copia della lista
    }
}
