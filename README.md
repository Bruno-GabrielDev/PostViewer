# 📱 PostViewer

Aplicativo Android que consome a API pública JSONPlaceholder para exibir posts e comentários, com suporte a comentários locais persistidos via Room.

## 👤 Identificação

| Item | Detalhe |
|------|---------|
| Aluno | Bruno Gabriel — sc3044122 |
| Disciplina | Dispositivos Móveis |
| Pacote | br.edu.ifsp.scl.sc3044122.postviewer |
| Min SDK | 26 (Android 8 / Oreo) |

## 📋 Descrição

O PostViewer consome a API pública [JSONPlaceholder](https://jsonplaceholder.typicode.com/) para listar posts e exibir seus comentários. O usuário também pode adicionar comentários locais que são persistidos no dispositivo via Room e aparecem junto aos comentários da API.

## ⚙️ Funcionalidades

### Tela 1 — Lista de Posts
- Carrega posts da API `GET /posts`
- Exibe título e prévia do conteúdo
- Estado de loading e erro com botão de retry
- Navega para a tela de detalhes ao tocar

### Tela 2 — Detalhes do Post
- Carrega comentários da API `GET /posts/{id}/comments`
- Exibe comentários locais junto aos da API
- Badge **"Local"** nos comentários adicionados pelo usuário
- Campo para adicionar novo comentário local
- Comentários locais persistem entre sessões

## ▶️ Como Rodar

1. Clone o repositório:
```bash
git clone https://github.com/Bruno-GabrielDev/PostViewer.git
```
2. Abra no **Android Studio** (Hedgehog ou superior)
3. Aguarde o sync do Gradle
4. Rode em um emulador ou dispositivo com Android 8+

## 🛠️ Tecnologias e Bibliotecas

| Tecnologia | Uso |
|---|---|
| Jetpack Compose | Interface do usuário |
| Navigation Compose | Navegação entre telas |
| ViewModel + StateFlow | Gerenciamento de estado |
| Room | Persistência local de comentários |
| Retrofit + Gson | Consumo da API REST |
| KSP | Geração de código do Room |

## 🏗️ Arquitetura

```
postviewer/
├── data/
│   ├── local/          ← Room (AppDatabase, DAO, Entity)
│   ├── remote/         ← Retrofit (ApiService, DTOs)
│   └── PostRepository  ← Orquestra API + banco local
├── domain/model/       ← Post, Comment (modelos de domínio)
├── presentation/
│   ├── posts/          ← PostsScreen + PostsViewModel
│   └── detail/         ← DetailScreen + DetailViewModel
└── di/AppModule        ← Injeção de dependências manual
```

## 💡 Decisões de Design

- **Arquitetura em camadas** (data / domain / presentation) para separação de responsabilidades
- **StateFlow** para estado reativo na UI sem precisar de LiveData
- **Flow no Room** para observar mudanças nos comentários locais em tempo real
- **KSP** no lugar de KAPT para geração de código mais rápida
- **Injeção manual** sem Hilt/Koin para manter o projeto simples
- **DTOs separados** dos modelos de domínio para desacoplar API da lógica de negócio

## 🖼️ Prints das Telas

Os prints do app em execução estão na pasta [`docs/`](./docs).
