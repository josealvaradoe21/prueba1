package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller


public class UserController {
	@Autowired
    private UserRepository repository;
	@GetMapping("/")
	private String basic(){
		return "redirect:/inicio";
	}
	@GetMapping("/inicio")
	private String basic1() {
		return "principal";
	}
	

    @GetMapping("/list")
    private String getAll(Model model){
        model.addAttribute("usuario", repository.findAll());
        return "user_list";
    }

    @GetMapping(path = {"/add", "edit/{id}"})
    private String addForm(@PathVariable("id") Optional<Long> id, Model model){
        if(id.isPresent()){
            model.addAttribute("usuarios", repository.findById(id.get()));
        }else{
            model.addAttribute("usuarios", new Usuario2());
        }
        return "add_edit_user";
    }

    @PostMapping("/addEdit")
    private String insertOrUpdate(Usuario2 usuario1){
        if(usuario1.getId() == null){
            repository.save(usuario1);
        }else{
            Optional<Usuario2> usuario1Optional = repository.findById(usuario1.getId());
            if(usuario1Optional.isPresent()){
                Usuario2 editUser = usuario1Optional.get();
                editUser.setCargo(usuario1.getCargo());
                editUser.setEdad(usuario1.getEdad());
                editUser.setNombre(usuario1.getNombre());
                editUser.setProvincia(usuario1.getProvincia());
                editUser.setDireccion(usuario1.getDireccion());
                repository.save(editUser);
            }
        }
        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    private String deleteUser(@PathVariable("id") Long id){
        Optional<Usuario2> user1 = repository.findById(id);
        if(user1.isPresent()){
            repository.delete(user1.get());
        }else{
            System.err.println("not found");
        }
        return "redirect:/list";
    }

}
