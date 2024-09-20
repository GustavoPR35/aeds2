package service;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

public class UsuarioService {

	private UsuarioDAO usuarioDAO;
	
	public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }
	
	public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
	
	public Object add(Request request, Response response) {
	    try {
	        int codigo = Integer.parseInt(request.queryParams("codigo"));
	        String login = request.queryParams("login");
	        String senha = request.queryParams("senha");
	        String sexoStr = request.queryParams("sexo");
	        char sexo = sexoStr.isEmpty() ? ' ' : sexoStr.charAt(0);

	        Usuario usuario = new Usuario(codigo, login, senha, sexo);
	        usuarioDAO.insert(usuario);
	        response.status(201); // Status HTTP 201 Created
	        return codigo;
	    } catch (NumberFormatException e) {
	        response.status(400); // 400 Bad Request
	        return "Código deve ser um número.";
	    } catch (Exception e) {
	        response.status(500); // 500 Internal Server Error
	        return "Erro ao cadastrar o usuário: " + e.getMessage();
	    }
	}
	
	public Object update(Request request, Response response) {
	    try {
	        int codigo = Integer.parseInt(request.params("id"));
	        String login = request.queryParams("login");
	        String senha = request.queryParams("senha");
	        String sexoStr = request.queryParams("sexo");
	        char sexo = sexoStr.isEmpty() ? ' ' : sexoStr.charAt(0);
	        
	        Usuario usuario = usuarioDAO.get(codigo);
	        if (usuario == null) {
	            response.status(404); // 404 Not Found
	            return "Usuário não encontrado.";
	        }

	        usuario.setLogin(login);
	        usuario.setSenha(senha);
	        usuario.setSexo(sexo);
	        usuarioDAO.update(usuario);
	        return codigo;
	    } catch (NumberFormatException e) {
	        response.status(400); // 400 Bad Request
	        return "Código deve ser um número.";
	    } catch (Exception e) {
	        response.status(500); // 500 Internal Server Error
	        return "Erro ao atualizar o usuário: " + e.getMessage();
	    }
	}
	
	public Object get(Request request, Response response) {
	    try {
	        int id = Integer.parseInt(request.params(":id")); // Usando params para pegar o ID
	        Usuario usuario = usuarioDAO.get(id);
	        if (usuario == null) {
	            response.status(404); // 404 Not Found
	            return "Usuário não encontrado.";
	        }
	        response.header("Content-Type", "application/xml");
	        response.header("Content-Encoding", "UTF-8");
	        return "<usuario>\n" + 
	                "\t<codigo>" + usuario.getCodigo() + "</codigo>\n" +
	                "\t<login>" + usuario.getLogin() + "</login>\n" +
	                "\t<senha>" + usuario.getSenha() + "</senha>\n" +
	                "\t<sexo>" + usuario.getSexo() + "</sexo>\n" +
	                "</usuario>\n";
	    } catch (NumberFormatException e) {
	        response.status(400); // 400 Bad Request
	        return "Código deve ser um número.";
	    } catch (Exception e) {
	        response.status(500); // 500 Internal Server Error
	        return "Erro ao buscar o usuário: " + e.getMessage();
	    }
	}

	
	public Object remove(Request request, Response response) {
	    try {
	        int codigo = Integer.parseInt(request.params("id"));
	        Usuario usuario = usuarioDAO.get(codigo);
	        if (usuario == null) {
	            response.status(404); // 404 Not Found
	            return "Usuário não encontrado.";
	        }
	        else if (usuarioDAO.delete(codigo)) {
	            return codigo;
	        } else {
	            response.status(404); // 404 Not Found
	            return "Usuário não encontrado.";
	        }
	    } catch (NumberFormatException e) {
	        response.status(400); // 400 Bad Request
	        return "Código deve ser um número.";
	    } catch (Exception e) {
	        response.status(500); // 500 Internal Server Error
	        return "Erro ao excluir o usuário: " + e.getMessage();
	    }
	}

	
	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<usuarios type=\"array\">");
		for (Usuario usuario : usuarioDAO.get()) {
			returnValue.append("\n<usuario>\n" + 
            		"\t<codigo>" + usuario.getCodigo() + "</codigo>\n" +
            		"\t<login>" + usuario.getLogin() + "</login>\n" +
            		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
            		"\t<sexo>" + usuario.getSexo() + "</sexo>\n" +
            		"</usuario>\n");
		}
		returnValue.append("</usuarios>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}

}
