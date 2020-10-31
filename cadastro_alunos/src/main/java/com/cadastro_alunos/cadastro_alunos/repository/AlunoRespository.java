package com.cadastro_alunos.cadastro_alunos.repository;



import com.cadastro_alunos.cadastro_alunos.models.Aluno;

import org.springframework.data.repository.CrudRepository;

public interface AlunoRespository extends CrudRepository<Aluno, String> {

	Aluno findByCodigo(long codigo);

    
}
