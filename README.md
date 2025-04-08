A aplicação desenvolvida permite o gerenciamento de funcionários com persistência em memória e geração de relatórios usando a Stream API.

Funcionalidades Implementadas

- Cadastro de funcionário com validações básicas.
- Interface gráfica utilizando JavaFX.
- Relatórios:
    - Filtro por cargo
    - Filtro por faixa salarial
    - Cálculo de média salarial por cargo
    - Agrupamento por cidade -> Não consegui fazer
    - Agrupamento por estado -> Não consegui fazer 

Decisões de Design

- JavaFX: Escolhido pela facilidade de construir interfaces por mais que não tenha tanto conhecimento é fácil de utilizar
- design Simples: Separação entre as classes existentes
- Uso de Stream API: Para operações funcionais como filtragem, agrupamento e média salarial.