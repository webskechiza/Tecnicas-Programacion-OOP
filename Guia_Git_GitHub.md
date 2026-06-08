# Guía Documentada de Git y GitHub

**Curso:** Técnicas de Programación — Orientación a Objetos  
**Alumno:** Kevin Chiri  
**Universidad:** UPN — Universidad Privada del Norte

---

## ¿Qué es Git?

Git es un **sistema de control de versiones distribuido**. Permite registrar el historial de cambios de un proyecto, trabajar en equipo sin sobrescribir el trabajo de otros, y volver a versiones anteriores del código cuando sea necesario.

## ¿Qué es GitHub?

GitHub es una **plataforma en la nube** que almacena repositorios Git de forma remota. Permite compartir código, colaborar con otros desarrolladores y mostrar proyectos públicamente.

---

## Comandos utilizados en este proyecto

### 1. Crear repositorio local

```bash
git init
```
Inicializa un repositorio Git vacío en la carpeta actual. Se crea la carpeta oculta `.git` que almacena todo el historial.

---

### 2. Configurar usuario

```bash
git config user.name "Kevin"
git config user.email "webskechiza@gmail.com"
```
Asocia un nombre y correo a cada commit realizado en este repositorio.

---

### 3. Agregar archivos al área de staging

```bash
git add Caso1_Calculadora.java
git add .
```
Prepara los archivos para ser incluidos en el próximo commit. `git add .` agrega todos los archivos modificados.

---

### 4. Crear un commit

```bash
git commit -m "commit 1: casos 1, 2 y 3 - calculadora, clase Persona y sobrecarga de metodos"
```
Guarda una instantánea del proyecto con un mensaje descriptivo. Cada commit queda registrado en el historial.

---

### 5. Ver historial de commits

```bash
git log --oneline
```
Muestra todos los commits del proyecto en formato resumido:

```
ad0b91b  commit 2: casos 4 y 5 - variable static y ArrayList con manejo de errores
4ad8bc0  commit 1: casos 1, 2 y 3 - calculadora, clase Persona y sobrecarga de metodos
```

---

### 6. Crear y cambiar entre ramas (branches)

```bash
git branch develop        # Crear la rama
git checkout develop      # Cambiar a la rama
```

O en un solo comando:

```bash
git checkout -b develop
```

Las ramas permiten trabajar en nuevas funcionalidades sin afectar el código principal (`master`).

---

### 7. Ver ramas existentes

```bash
git branch
```

Muestra todas las ramas locales. La rama activa aparece con `*`:

```
* develop
  master
```

---

### 8. Fusionar ramas (merge)

```bash
git checkout master       # Volver a master
git merge develop         # Fusionar develop en master
```

Integra los cambios de la rama `develop` dentro de `master`.

---

### 9. Conectar con GitHub (repositorio remoto)

```bash
git remote add origin https://github.com/webskechiza/Tecnicas-Programacion-OOP.git
```

Vincula el repositorio local con el repositorio remoto en GitHub.

---

### 10. Subir commits a GitHub (push)

```bash
git push -u origin master
```

Envía los commits locales al repositorio remoto. Con `-u` se establece el seguimiento automático para futuros push.

---

### 11. Clonar un repositorio de GitHub

```bash
git clone https://github.com/webskechiza/Tecnicas-Programacion-OOP.git
```

Descarga una copia completa del repositorio remoto (incluyendo todo el historial de commits) en la máquina local.

---

### 12. Ver estado del repositorio

```bash
git status
```

Muestra qué archivos tienen cambios, cuáles están en staging y cuáles no han sido rastreados.

---

## Flujo de trabajo utilizado en este proyecto

```
1. git init                        ← Crear repo local
2. git add <archivos>              ← Preparar cambios
3. git commit -m "mensaje"         ← Guardar snapshot
4. git checkout -b develop         ← Crear rama de desarrollo
5. (hacer cambios en develop)
6. git merge develop               ← Fusionar a master
7. git push -u origin master       ← Subir a GitHub
```

---

## Estructura del repositorio

```
Tecnicas-Programacion-OOP/
├── README.md
├── Guia_Git_GitHub.md        ← este archivo
├── Semana1/
│   └── Taller_Semana1_2/
│       └── src/
│           ├── Caso1_Calculadora.java
│           ├── Caso2_Persona.java
│           ├── Caso3_Sobrecarga.java
│           ├── Caso4_Contador.java
│           └── Caso5_Estudiantes.java
└── Semana2/                  ← próximas semanas
```

---

> Repositorio: https://github.com/webskechiza/Tecnicas-Programacion-OOP
