# Técnicas de Programación — Orientación a Objetos

Repositorio del curso **Técnicas de Programación OOP** — UPN Kevin Oswaldo Chirinos Zapata - N00521954 - Grupo 28

## Estructura

```
Tecnicas-Programacion-OOP/
├── Semana1/
│   └── Taller_Semana1_2/
│       └── src/
│           ├── Caso1_Calculadora.java
│           ├── Caso2_Persona.java
│           ├── Caso3_Sobrecarga.java
│           ├── Caso4_Contador.java
│           └── Caso5_Estudiantes.java
├── Semana3/
│   ├── VetCare_Documentos/
│   │   ├── VetCare_Completo_Terminado.docx
│   │   ├── VetCare_Avance_Fase1_ENTREGA.docx
│   │   ├── ishikawa_vetcare.png
│   │   └── uml_vetcare.png
│   └── src/Taller_Semana3/
│       ├── Veterinario.java
│       ├── Mascota.java
│       └── Main.java
```

## Semana 1 — Taller práctico

| Caso | Clase | Tema |
|------|-------|------|
| 1 | `Caso1_Calculadora` | Métodos estáticos con parámetros y retorno |
| 2 | `Caso2_Persona` | Clase con atributos, constructor y métodos |
| 3 | `Caso3_Sobrecarga` | Sobrecarga de métodos |
| 4 | `Caso4_Contador` | Variable estática compartida entre objetos |
| 5 | `Caso5_Estudiantes` | ArrayList + manejo de excepciones |

## Semana 3 — Proyecto VetCare

**Tema:** Sistema de Gestión de Clínica Veterinaria

| Archivo | Descripción |
|---------|-------------|
| `Veterinario.java` | Sobrecarga de `registrarConsulta()` — 3 firmas |
| `Mascota.java` | `ArrayList<String>` para historial + `try-catch` |
| `Main.java` | Demo de sobrecarga, colecciones y manejo de errores |
| `VetCare_Avance_Fase1_ENTREGA.docx` | Avance Fase 1 (secciones 1-6) para entrega |
| `ishikawa_vetcare.png` | Diagrama de Ishikawa — causas de ineficiencia operativa |
| `uml_vetcare.png` | Diagrama de clases UML — 13 clases con herencia y composición |

### Evidencia de prácticas Git

- `git stash` / `git stash pop` — guardar y recuperar trabajo en progreso
- `git revert` — revertir commit con error sin destruir historial
- `git cherry-pick` — aplicar commit de rama `hotfix` en rama `feature`
- Merge con `--no-ff` para preservar historial de rama
