package com.example.bancofuncional.service;

import com.example.bancofuncional.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseService {

    // Garantindo que o caminho está correto
    private static final String DB_FILE = "C:/temp/banco/usuarios.json";
    private static final Gson gson = new Gson();

    public static void salvarUsuarios(Map<String, Usuario> usuarios) {
        System.out.println("==> [DatabaseService] Entrou no método salvarUsuarios.");
        System.out.println("==> [DatabaseService] Tentando escrever no arquivo: " + DB_FILE);

        try (FileWriter writer = new FileWriter(DB_FILE)) {
            gson.toJson(usuarios, writer);
            System.out.println("*************************************************");
            System.out.println("SUCESSO! DADOS SALVOS EM: " + DB_FILE);
            System.out.println("*************************************************");
        } catch (IOException e) {
            // Se der erro ao escrever, esta mensagem detalhada aparecerá.
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.err.println("ERRO CRÍTICO DE IO: Não foi possível escrever o arquivo.");
            System.err.println("Caminho do arquivo: " + DB_FILE);
            System.err.println("Verifique se a pasta C:\\temp\\banco existe e se você tem permissão para escrever nela.");
            System.err.println("Mensagem do erro Java: " + e.getMessage());
            System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }

    public static Map<String, Usuario> carregarUsuarios() {
        try (FileReader reader = new FileReader(DB_FILE)) {
            Type type = new TypeToken<ConcurrentHashMap<String, Usuario>>(){}.getType();
            Map<String, Usuario> usuarios = gson.fromJson(reader, type);
            System.out.println("Dados carregados com sucesso de: " + DB_FILE);
            return usuarios != null ? usuarios : new ConcurrentHashMap<>();
        } catch (IOException e) {
            System.out.println("Arquivo de banco de dados não encontrado. Iniciando com base de dados vazia.");
            return new ConcurrentHashMap<>();
        }
    }
}