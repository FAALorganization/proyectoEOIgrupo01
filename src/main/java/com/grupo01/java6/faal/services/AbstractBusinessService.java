package com.grupo01.java6.faal.services;
import com.grupo01.java6.faal.services.mappers.AbstractServiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public abstract class AbstractBusinessService <E, ID, DTO,  REPO extends JpaRepository<E,ID> ,
        MAPPER extends AbstractServiceMapper<E,DTO>>  {
    protected final REPO repo;
    final MAPPER serviceMapper;


    protected AbstractBusinessService(REPO repo, MAPPER mapper) {
        this.repo = repo;
        this.serviceMapper = mapper;
    }

    //Busca una entidad por ID, retorna la entidad directamente.
    public Optional<E> buscarEntity(ID id){
        return  this.repo.findById(id);
    }

    //Devuelve una lista de todos los elementos convertidos a DTO.
    public List<DTO> buscarAllDTO(){
        return  this.serviceMapper.toDto(this.repo.findAll());
    }

    //Devuelve todas las entidades sin conversión.
    public List<E> buscarAllEntity(){
        return  this.repo.findAll();
    }

    //Devuelve todas las entidades sin conversión en un Set para eliminar duplicados.
    public Set<E> buscarAllEntitySet(){
        return new HashSet<E>(this.repo.findAll());
    }

    //Devuelve los DTOs sin duplicados.
    public Set<DTO> buscarAllDTOSet(){
        return new HashSet<DTO>(this.serviceMapper.toDto(this.repo.findAll()));
    }

    //Devuelve una página de DTOs, útil para paginación.
    public Page<DTO> buscarAllDTOPage(Pageable pageable){
        return  repo.findAll(pageable).map(this.serviceMapper::toDto);
    }

    //Buscar por id retornando un DTO.
    public Optional<DTO> buscarByIdDTO(ID id){

        return this.repo.findById(id).map(this.serviceMapper::toDto);
    }

    //Busca por id retornando una entidad pura.
    public Optional<E> buscarByIdEntity(ID id){

        return this.repo.findById(id);
    }

    //Recibe un DTO, lo convierte a entidad, guarda y vuelve a convertir a DTO.
    public DTO guardarDTODTO(DTO dto) throws Exception {
        //Traduzco del dto con datos de entrada a la entidad
        final E entidad = serviceMapper.toEntity(dto);
        //Guardo el la base de datos
        E entidadGuardada =  repo.save(entidad);
        //Traducir la entidad a DTO para devolver el DTO
        return serviceMapper.toDto(entidadGuardada);
    }

    //Guarda una entidad y retorna su DTO.
    public DTO guardarEntityDTO(E entidad)  {
        //Guardo el la base de datos
        E entidadGuardada =  repo.save(entidad);
        //Traducir la entidad a DTO para devolver el DTO
        return serviceMapper.toDto(entidadGuardada);
    }

    //Guarda y devuelve la entidad como tal.
    public E guardarEntityEntity(E entidad) throws Exception {
        //Guardo el la base de datos
        E entidadGuardada =  repo.save(entidad);
        //Traducir la entidad a DTO para devolver el DTO
        return entidadGuardada;
    }

    //Guarda una lista de DTOs uno por uno en la ddbb.
    public void  guardarListDTO(List<DTO> dtos) throws Exception {
        Iterator<DTO> it = dtos.iterator();

        // mientras al iterador queda proximo juego
        while(it.hasNext()){
            //Obtenemos la password de a entidad
            //Datos del usuario
            E e = serviceMapper.toEntity(it.next());
            repo.save(e);
        }
    }

    //Eliminar un registro.
    public void eliminarById(ID id){
        this.repo.deleteById(id);
    }

    //Obtener el mapper
    public MAPPER getMapper(){return  serviceMapper;}
    //Obtener el repo
    public REPO getRepo(){return  repo;}
}