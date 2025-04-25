package cl.ntt.postulacion.api.usuario;


import cl.ntt.postulacion.api.usuario.model.UsuarioAccess;
import cl.ntt.postulacion.api.usuario.model.UsuarioCrea;
import cl.ntt.postulacion.api.usuario.model.UsuarioPost;
import cl.ntt.postulacion.response.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UsuarioController {
    private final UsuarioOperations op;

    @Autowired
    public UsuarioController(UsuarioOperations op){
        this.op = op;
    }

    @PostMapping("/ingreso")
    @Operation(summary="se obtiene token para el usuario ingresado")
    public ResponseEntity<GenericResponse<String>> ingreso(@Valid @RequestBody UsuarioAccess usuario){
        return ResponseEntity.ok(op.ingreso(usuario));
    }


    @PostMapping("/creausuario")
    @Operation(summary="crea  un usuario")
    public ResponseEntity<GenericResponse<UsuarioCrea>> crear(@Valid @RequestBody UsuarioPost usuario){
        return ResponseEntity.ok(op.crear(usuario));
    }
    @PutMapping("/editausuario")
    @Operation(summary="edita  usuario")
    public ResponseEntity<GenericResponse<String>> editar(@Valid @RequestBody UsuarioPost usuario){
        return ResponseEntity.ok(op.edita(usuario));
    }
    @PatchMapping("/passusuario")
    @Operation(summary="edita  password de  usuario")
    public ResponseEntity<GenericResponse<String>> modificarPass(@Valid @RequestBody UsuarioPost usuario){
        return ResponseEntity.ok(op.editapass(usuario));
    }

    @DeleteMapping("/borrarusuario/{id_usuario}")
    @Operation(summary="borra  usuario")
    public ResponseEntity<GenericResponse<String>> borrar(
            @PathVariable("id_usuario") int id_usuario) {
        return ResponseEntity.ok(op.borrar(id_usuario));
    }
    @GetMapping("/usuario/{id_usuario}")
    @Operation(summary="Retorna un usuario .")
    public ResponseEntity<GenericResponse<UsuarioPost>> traerusuario(
            @PathVariable("id_usuario") int id_usuario) {
        return ResponseEntity.ok(op.traer(id_usuario));
    }

    @GetMapping("/usuarioS")
    @Operation(summary="Retorna usuarios .")
    public ResponseEntity<GenericResponse<List<UsuarioPost>>> todos() {
        return ResponseEntity.ok(op.todos());
    }
}
