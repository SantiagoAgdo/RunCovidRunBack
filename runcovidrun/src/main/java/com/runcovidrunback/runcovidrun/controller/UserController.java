package com.runcovidrunback.runcovidrun.controller;


import com.runcovidrunback.runcovidrun.Encry.encri;
import com.runcovidrunback.runcovidrun.dto.UserDto;
import com.runcovidrunback.runcovidrun.dto.ResponseDto;
import com.runcovidrunback.runcovidrun.entity.GetData;
import com.runcovidrunback.runcovidrun.entity.User;
import com.runcovidrunback.runcovidrun.services.DataService;
import com.runcovidrunback.runcovidrun.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DataService dataService;

    public static encri encryp = new encri();

    @GetMapping("/list")
    public ResponseEntity<List<User>> list(){
        List<User> list = userService.listaUsers();
        return  new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/data")
    public ResponseEntity<List<GetData>> listData(){
        List<GetData> listData = dataService.data();
        System.out.println("=================");
        for (int i = 0; i <listData.size() ; i++) {
            System.out.println(listData);
        }
        return  new ResponseEntity(listData, HttpStatus.OK);
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> create(@RequestBody UserDto userDto){
        String encriptado = "";
        if (StringUtils.isBlank(userDto.getName())){
            return new ResponseEntity(new ResponseDto("El Nombre Es Obligatorio"), HttpStatus.BAD_REQUEST);
        }
        if (userDto.getDateCreation() == null){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String formatted = format1.format(c.getTime());
            userDto.setDateCreation(formatted);
//            return new ResponseEntity(new ResponseDto("Fecha de creacion es necesaria para el registro"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByName(userDto.getName())){
            return new ResponseEntity(new ResponseDto("Este nombre ya existe!"), HttpStatus.CONFLICT);
        }
        if (userDto.getPass() == null  ){
            return  new ResponseEntity(new ResponseDto("Es necesario una contrasena"), HttpStatus.BAD_REQUEST);
        }
        if (userDto.getPass().length() < 4){
            return  new ResponseEntity(new ResponseDto("La contrasena debe ser mayor a o igual a 4 digitos"), HttpStatus.BAD_REQUEST);
        }

        try {
             encriptado = encryp.encriptar(userDto.getPass(), "covidrun");


        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(encri.class.getName()).log(Level.SEVERE, null, ex);
        }

        User  user = new User(userDto.getName(), encriptado ,userDto.getScore(), userDto.getDateCreation());
        userService.save(user);
        return new ResponseEntity(new ResponseDto("Usuario Creado"), HttpStatus.OK);
    }

    @PostMapping("/checkUser")
    public ResponseEntity<?> checkUser(@RequestBody User userDto){
        String desencripty = "";
        if (StringUtils.isBlank(userDto.getName())){
            return new ResponseEntity(new ResponseDto("Nombre no obtenido"), HttpStatus.NOT_FOUND);
        }
        String SecretPass = "covidrun";
        if (userDto.getPass() == null || userDto.getPass().length() < 4 ){
            return  new ResponseEntity(new ResponseDto("Contrasena no obtenida"), HttpStatus.NOT_FOUND);
        }
        try {
            String encriptado = encryp.encriptar(userDto.getPass(), SecretPass);
            desencripty = encryp.desencriptar(encriptado, SecretPass);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(encri.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (desencripty == null || desencripty.length() < 2  ){
            return  new ResponseEntity(new ResponseDto("Descriptacion fallida"), HttpStatus.CONFLICT);
        }

        boolean userExists = userService.existsByName(userDto.getName());
        boolean passIsValid = userService.passIsValid(desencripty);

        if( !userExists  ){
            return  new ResponseEntity(new ResponseDto("Usuario y/o contrasena invalida"), HttpStatus.NOT_FOUND);
        }
        if( !passIsValid){
            return  new ResponseEntity(new ResponseDto("Usuario y/o contrasena invalida 2"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity( new ResponseDto("Usuario Valido"), HttpStatus.OK);
    }

    @PutMapping("/updateuser/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody UserDto userDto){
        if (!userService.existsById(id)){
            return  new ResponseEntity(new ResponseDto("Usuario no Existe"), HttpStatus.NOT_FOUND);
        }
        if (userService.existsByName(userDto.getName())){
            return new ResponseEntity<>(new ResponseDto("Nombre ya existe!"), HttpStatus.CONFLICT);
        }
        if (StringUtils.isBlank(userDto.getName())){
            return  new ResponseEntity(new ResponseDto("El nombre es obligatorio"), HttpStatus.NOT_FOUND);
        }
        if (StringUtils.isBlank(userDto.getPass()) || userDto.getPass().length() < 4 ) {
            return  new ResponseEntity(new ResponseDto("Contrasena no es valida"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.getOne(id).get();
        user.setName(userDto.getName());
        user.setDateCreation(userDto.getDateCreation());
        user.setScore(userDto.getScore());
        user.setPass(userDto.getPass());
        userService.save(user);
        return new ResponseEntity(new ResponseDto("Usuario Actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if (!userService.existsById(id)){
            return new ResponseEntity(new ResponseDto("Usuario No Existente"), HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return  new ResponseEntity( new ResponseDto("Usuario Eliminado"), HttpStatus.OK);
    }
}
