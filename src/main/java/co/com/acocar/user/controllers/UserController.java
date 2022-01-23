package co.com.acocar.user.controllers;

import java.util.List;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.acocar.user.dao.UserDao;
import co.com.acocar.user.dto.AuthCredentialsDto;
import co.com.acocar.user.model.User;
import co.com.acocar.user.util.Util;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value="user")
public class UserController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	Util util;
	
	@Operation(summary="Obtener listado de usuarios")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "400", description = "Error credentials")
	@GetMapping(value = "/")
	public ResponseEntity<?> getUSerList(@RequestHeader(value="token",required = true) String token) throws Exception{
		List<User> list = null;
		
		if(validarToken(token)) {
			list = userDao.getUsers();
			return ResponseEntity.ok().body(list);
		}
		return ResponseEntity.badRequest().body("Token no valido");
	}
	
	@Operation(summary="Obtener usuario por id")
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "400", description = "Error credentials")
	@GetMapping(value="/{id}")
	public ResponseEntity<?> getUSer(@RequestHeader(value="token",required = true) String token,@PathVariable(required = true) Long id) {
		if(validarToken(token)) {
			User u = userDao.getUSerById(id);
			return ResponseEntity.ok().body(u);
		}
		return ResponseEntity.badRequest().body("Token no valido");
	}
	
	@Operation(summary="Creacion de usuarios")
	@ApiResponse(responseCode = "200", description = "usuario creado",content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Error credentials")
	@PostMapping(value="/")
	public ResponseEntity<?> createUser(@RequestBody User u) {
		
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String pass = argon2.hash(1, 1024, 1, u.getPassword());
		u.setPassword(pass);
		User usuario = userDao.createUSer(u);
		return ResponseEntity.ok().body("usuario creado exitosamente: " + usuario.getId());
	}
	
	@Operation(summary="Obtener token de sesion por credenciales")
	@ApiResponse(responseCode = "200", description = "credenciales correctas",content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Error credentials")
	@PostMapping(value="/auth")
	public ResponseEntity<?> login(@RequestBody AuthCredentialsDto auth){
		User result = userDao.validUser(auth);
		if(result != null) {
			return ResponseEntity.ok().body(util.create(result.getId().toString(), result.getName()));
		}
		return ResponseEntity.badRequest().body("Error credenciales");
		
	}
	
	@Operation(summary="Actualizar usuario")
	@ApiResponse(responseCode = "200", description = "usuario actualizado",content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Error credentials")
	@PutMapping(value="/")
	public ResponseEntity<?> updateUser(@RequestHeader(value="token",required = true) String token,@RequestBody User u) {
		if(validarToken(token)) {
			User usuario = userDao.updateUser(u);
			return ResponseEntity.ok().body("usuario actualizado exitosamente: " + usuario.getId());
		}
		return ResponseEntity.badRequest().body("Token no valido");
	}
	
	@Operation(summary="Eliminar usuario")
	@ApiResponse(responseCode = "200", description = "usuario eliminado",content = @Content(schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Error credentials")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<?> deleteUser(@RequestHeader(value="token",required = true) String token,@PathVariable(required = true) Long id) {
		if(validarToken(token)) {
			userDao.deleteUser(id);
			return ResponseEntity.ok().body("usuario eliminado exitosamente");
		}
		return ResponseEntity.badRequest().body("Token no valido");
	}
	
	private boolean validarToken(String token) {
		return token.isBlank() ? false : util.getValue(token) != null ? true : false;
	}
}
