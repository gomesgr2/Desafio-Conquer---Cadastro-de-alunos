package com.cadastro_alunos.cadastro_alunos.controller;

import javax.validation.Valid;

import com.cadastro_alunos.cadastro_alunos.models.Aluno;
import com.cadastro_alunos.cadastro_alunos.repository.AlunoRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AlunoController {

    @Autowired 
    private AlunoRespository er;


    @RequestMapping(value = "/cadastro_alunos", method = RequestMethod.GET)
    public String form(){
        return "Aluno/formAluno";
    }
    
    
    @RequestMapping(value = "/cadastro_alunos", method = RequestMethod.POST)
    public String form(@Valid Aluno aluno,  BindingResult result, RedirectAttributes attributes){
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Cadastro inválido, verifique os campos");
            return "redirect:/cadastro_alunos";
        }
        er.save(aluno);
        attributes.addFlashAttribute("mensagem","Cadastro realizado com sucesso :)");
        return "redirect:/cadastro_alunos";
    }


    @RequestMapping("/lista_alunos")
    public String lista_alunos(Aluno aluno){
        return "Aluno/lista_alunos";
    }
    @RequestMapping(value = "/lista_alunos", method = RequestMethod.GET)
    public ModelAndView lista_alunos() {
        ModelAndView mv = new ModelAndView("Aluno/lista_alunos");
        Iterable<Aluno> aluno = er.findAll();
        mv.addObject("aluno", aluno);
        return mv;
    }
    
    @RequestMapping("/deletar")
    public String deletar_aluno(long codigo) {
        Aluno aluno = er.findByCodigo(codigo);
        er.delete(aluno);
        return "redirect:/lista_alunos";
    }
    
    @RequestMapping(value ="/editar/{codigo}")
    public String editar(@PathVariable("codigo") String codigo) {
        return "Aluno/editar";

    }
    @RequestMapping(value = "/editar/{codigo}", method = RequestMethod.GET)
    public ModelAndView editar(@PathVariable("codigo") long codigo) {
        ModelAndView mv = new ModelAndView("Aluno/editar");
        Aluno esp_aluno = er.findByCodigo(codigo);
        mv.addObject("aluno_id", esp_aluno);
        return mv;
    }
    @PostMapping(value = "/editar/{codigo}")
    public String salvar(@PathVariable("codigo") long codigo,@Valid Aluno novo_aluno,  BindingResult result, RedirectAttributes attributes )
    {   
        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Cadastro inválido, verifique os campos");
            return "redirect:/editar/{codigo}";
        }
        
        Aluno antigo_aluno = er.findByCodigo(codigo);
        antigo_aluno.setNome(novo_aluno.getNome());
        antigo_aluno.setIdade(novo_aluno.getIdade());
        antigo_aluno.setEmail(novo_aluno.getEmail());
        er.save(novo_aluno);
        attributes.addFlashAttribute("mensagem","Edição realizada com sucesso :)");
        return "redirect:/editar/{codigo}";
    }



}
   
    
