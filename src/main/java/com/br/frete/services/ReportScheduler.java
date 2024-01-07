package com.br.frete.services;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;



import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ReportScheduler {

    private final FreteService freteService;

    public ReportScheduler(FreteService freteService) {
        this.freteService = freteService;
    }

    @Scheduled(cron = "0 23 18 * * ?")
    public String generateReport() throws JsonProcessingException {
        List<Map<String, Object>> fretesConcluidos = freteService.getFretesConcluidos();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(fretesConcluidos);
        System.out.println("JSON gerado: " + json);

        String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String relatorio = "relatorios/fretes-" + dataAtual + ".json";

        try {
            
            Path directory = Paths.get("relatorios");
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                System.out.println("Diretório criado: " + directory.toAbsolutePath());
            }

            
            try (FileWriter file = new FileWriter(relatorio)) {
                file.write(json);
                file.flush();
                System.out.println("Relatório escrito: " + Paths.get(relatorio).toAbsolutePath());
            } catch (IOException e) {
                System.out.println("Erro ao escrever o relatório: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Erro ao criar o diretório: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Relatório salvo em " + relatorio);
        return relatorio;
    }
}