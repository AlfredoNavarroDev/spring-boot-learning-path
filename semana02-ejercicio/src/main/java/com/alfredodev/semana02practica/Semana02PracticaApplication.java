package com.alfredodev.semana02practica;

import com.alfredodev.semana02practica.config.EntornoConfig;
import com.alfredodev.semana02practica.dto.ProductoStockDto;
import com.alfredodev.semana02practica.entity.Producto;
import com.alfredodev.semana02practica.repository.ProductoRepository;
import com.alfredodev.semana02practica.service.InventarioService;
import com.alfredodev.semana02practica.service.ProductoService;
import com.alfredodev.semana02practica.util.PrecioFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.time.Clock;

// @SpringBootApplication = @Configuration + @ComponentScan + @EnableAutoConfiguration.
// Escanea com.alfredodev.semana02practica y todos sus subpaquetes buscando beans.
@SpringBootApplication
public class Semana02PracticaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Semana02PracticaApplication.class, args);

        // ============================================================
        // Descomentá cada bloque A MEDIDA que completes el paso.
        // Verificar compilación:  mvn compile
        // Probar:                 mvn spring-boot:run
        // ============================================================

        // ── Paso 2: repository ─────────────────────────────────────
        ProductoRepository repo = ctx.getBean(ProductoRepository.class);
        System.out.println("Productos en repo: " + repo.listarTodos());

        // ── Paso 3: service + constructor DI ───────────────────────
        ProductoService ps = ctx.getBean(ProductoService.class);
        System.out.println("Cantidad de productos: " + ps.listarTodos().size());

        // ── Pasos 4-5: orquestador + DTO ───────────────────────────
        InventarioService inv = ctx.getBean(InventarioService.class);
        System.out.println(inv.revisarStock("Laptop HP", 5));
        System.out.println(inv.revisarStock("Impresora", 5));

        // ── Paso 6: @Configuration + @Bean ─────────────────────────
        Clock clock = ctx.getBean(Clock.class);
        PrecioFormatter pf = ctx.getBean(PrecioFormatter.class);
        System.out.println("Hora (vía Clock @Bean): " + clock.instant());
        for (Producto p : ctx.getBean(ProductoService.class).listarTodos()) {
             System.out.println("  " + p.nombre() + " — " + pf.formatear(p.precio()));
        }

        // ── Paso 7: @Value + ciclo de vida ─────────────────────────
        ProductoStockDto dto = ctx.getBean(InventarioService.class).revisarStock("Laptop HP");
        System.out.println("Laptop HP con umbral por defecto → " + dto.estado());

        // ── Paso 8: profiles ───────────────────────────────────────
        Environment env = ctx.getEnvironment();
        String[] activos = env.getActiveProfiles();
        System.out.println("Perfiles activos: " + (activos.length == 0 ? "(default)" : String.join(",", activos)));
        System.out.println("inventario.umbral-stock resuelto: " + env.getProperty("inventario.umbral-stock"));
        System.out.println("Beans @Profile: " + ctx.getBeansOfType(EntornoConfig.class).values());

        // Cerrar el contexto dispara @PreDestroy en los beans singleton (Paso 7).
        ctx.close();
    }
}
