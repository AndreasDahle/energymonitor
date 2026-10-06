package com.energi.backend;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/components")
public class ComponentController {
    private final ComponentService componentService;

    public ComponentController (ComponentService componentService){
        this.componentService=componentService;
    }
    @GetMapping
    public List<Component> getallComponents(){
        return componentService.getAllComponents();
    }

    @GetMapping("/{id}")
    public Component getComponent(@PathVariable Long id) {
        return componentService.getComponentByID(id);
    }

    @PostMapping
    public ResponseEntity<Component> createComponent(@Valid @RequestBody Component component){
        Component created = componentService.newComponent(component);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/{id}")
    public  Component updateComponent(@PathVariable Long id, @Valid @RequestBody Component component){
        return componentService.updateComponent(id, component);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id){
            componentService.deleteComponent(id);
            return ResponseEntity.noContent().build();
    }
}
