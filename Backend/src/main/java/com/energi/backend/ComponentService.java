package com.energi.backend;


import com.energi.backend.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComponentService {


    private final ComponentRepository componentRepository;

    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }
    public Component newComponent(Component component){
           return componentRepository.save(component);
    }
    public List<Component> getAllComponents(){
        return(componentRepository.findAll());
    }
    public Component getComponentByID(Long id){
        return componentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Component not found"));
    }
    public void deleteComponent(Long id){
        componentRepository.deleteById(id);
    }
    public Component updateComponent(Long id, Component component){
        Component exists = componentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Component not found"));
        exists.setName(component.getName());
        exists.setType(component.getType());
        exists.setStatus(component.getStatus());
        return componentRepository.save(exists);
    }
}
