# 📱 PostViewer

Aplicativo Android que consome a API pública JSONPlaceholder para exibir posts e comentários, com suporte a comentários locais persistidos via Room.

## 📋 Informações do Projeto

| Item | Detalhe |
|------|---------|
| Aluno | sc3044122 |
| Pacote | br.edu.ifsp.scl.sc3044122.postviewer |
| Min SDK | 26 (Android 8 / Oreo) |
| Linguagem | Kotlin |
| UI | Jetpack Compose |

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

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| Jetpack Compose | Interface do usuário |
| Navigation Compose | Navegação entre telas |
| ViewModel + StateFlow | Gerenciamento de estado |
| Room | Persistência local de comentários |
| Retrofit + Gson | Consumo da API REST |

## ▶️ Como Rodar

1. Clone o repositório
2. Abra no **Android Studio** (Hedgehog ou superior)
3. Aguarde o sync do Gradle
4. Rode em um emulador ou dispositivo com Android 8+

## 💡 Decisões de Design

- **Arquitetura em camadas** (data / domain / presentation) para separação de responsabilidades
- **StateFlow** para estado reativo na UI sem precisar de LiveData
- **Flow no Room** para observar mudanças nos comentários locais em tempo real
- **Injeção manual** sem Hilt/Koin para manter o projeto simples
- **DTOs separados** dos modelos de domínio para desacoplar API da lógica de negócio
- **KSP no lugar de KAPT para o Room**. KSP é significativamente mais rápido e é a recomendação oficial atual da equipe do
AndroidX.
