 CREATE PROCEDURE crear_usuario(
	OUT id_user INT, 
	IN nombre_in VARCHAR(50), 
	IN correo_in VARCHAR(50), 
	IN password_in VARCHAR(50),
	IN token_in VARCHAR(50))
   MODIFIES SQL DATA
   BEGIN ATOMIC

	
	
     INSERT INTO usuario VALUES (DEFAULT, 
	 nombre_in, 
	 correo_in, 
	 password_in, 
	 CURRENT_TIMESTAMP, 
	 null, 
	 null, token_in, 1);
     SET id_user = IDENTITY();

   END
   
    CREATE PROCEDURE borrar_usuario(
	in id_user INT)
   MODIFIES SQL DATA
   BEGIN ATOMIC

     delete from telefono where id_usuario = id_user;
	 delete from usuario where id = id_user;

   END
  CREATE PROCEDURE add_telefono(
	in id_user INT, 
	IN numero_in VARCHAR(15), 
	IN codciudad_in VARCHAR(5), 
	IN codpais_in VARCHAR(5))
   MODIFIES SQL DATA
   BEGIN ATOMIC

     INSERT INTO telefono VALUES (DEFAULT, 
	 id_user, 
	 numero_in, 
	 codciudad_in, 
	 codpais_in);
     

   END
   
   CREATE PROCEDURE borrar_telefonos(
	in id_user INT)
   MODIFIES SQL DATA
   BEGIN ATOMIC
		delete from telefono where id_usuario = id_user;
     

   END
   
    CREATE PROCEDURE modificar_usuario(

	IN correo_in VARCHAR(50), 
	IN password_in VARCHAR(50),
	IN token_in VARCHAR(50))
   MODIFIES SQL DATA
   BEGIN ATOMIC
		update usuario set

		password = password_in,
		token = token_in,
		modificado = CURRENT_TIMESTAMP
		where  correo = correo_in;
     

   END
   
 CREATE PROCEDURE get_usuario(IN id_user int) 
                    READS SQL DATA DYNAMIC RESULT SETS 1 
                    BEGIN ATOMIC 
                    DECLARE result CURSOR WITH RETURN FOR 

                     SELECT id, nombre, correo, password,
					 token, activo
					 FROM usuario WHERE id = id_user;
                    OPEN result; 
                    END
 CREATE PROCEDURE get_usuario_creado(IN id_user int) 
                    READS SQL DATA DYNAMIC RESULT SETS 1 
                    BEGIN ATOMIC 
                    DECLARE result CURSOR WITH RETURN FOR 

                     SELECT id, creado, modificado, last_login
					 token, activo
					 FROM usuario WHERE id = id_user;
                    OPEN result; 
                    END					
					
 CREATE PROCEDURE get_usuarios() 
                    READS SQL DATA DYNAMIC RESULT SETS 1 
					                    BEGIN ATOMIC 
                    DECLARE result CURSOR WITH RETURN FOR 

                     SELECT id, nombre, correo, password,
					 token, activo
					 FROM usuario; 
                    OPEN result; 
                    END	

 CREATE PROCEDURE get_validar(IN correo_in VARCHAR(50)) 
                    READS SQL DATA DYNAMIC RESULT SETS 1 
					            BEGIN ATOMIC 
                    DECLARE result CURSOR WITH RETURN FOR 
        
                     SELECT id, nombre, correo, password,
					 token, activo
					 FROM usuario WHERE correo = correo_in; 
                    OPEN result; 
                    END				
				
   