package com.energi.backend;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.cglib.core.Local;

@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String type;
    private LocalDateTime lastUpdated;

    public enum Status {
        ACTIVE,
        INACTIVE,
        MAINTENANCE
    }
    public Component(String name, Status status, String type){
        this.name=name;
        this.status=status;
        this.type=type;
    }


    public Component(){
            }
    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }
    public void  setName(String name){
        this.name=name;
    }
    public Status getStatus(){
        return status;
    }
    public void setStatus(Status status){
        this.status=status;

    }
    public String getType(){
        return type;
    }
    public void  setType(String type){
        this.type=type;
    }

    @PrePersist
    public void onCreate() {
        lastUpdated = LocalDateTime.now().withNano(0);
    }

    @PreUpdate
    public void onUpdate() {
        lastUpdated = LocalDateTime.now().withNano(0);
    }
    public LocalDateTime getLastUpdated(){
        return lastUpdated;
    }

}
