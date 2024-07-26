# Wind_Charge Plugin

---
## Visão Geral

O plugin `Wind_Charge` gerencia a personalização do Wind Charge, permitindo controlar velocidade, particulas e a força do knockback. 


## Instalação

### automatica:

1. acesse o repositório com a automação [docker](https://github.com/PauloFH/Teste-de-Recrutamento-Developer-Java-Junior)
2. siga as instruções do repositório

### manual:

1. Baixe o arquivo `.jar` do plugin localizado em: `build/libs/Wind_Charge-1.0-SNAPSHOT.jar`
2. Coloque o arquivo  na pasta `plugins` do seu servidor Minecraft.
3. Reinicie o servidor.


## Comandos


- **Uso Básico**: Adiciona uma pilha de "Wind Charge" ao inventário do jogador que executa o comando.

  ```
  /windcharger
  ```

- **Configuração**: Permite configurar as propriedades do Wind Charge.

  ```
  /windcharger configure <power/particles/count-particles/speed> <valor>
  ``` 
  - **speed**: Define a velocidade do projétil de vento (de 1 a 5).
    - Exemplo: `/windcharger configure speed 1.0`
  - **power**: Define a força do vento de (1 a 10) .
    - Exemplo: `/windcharger configure power 0.5`
  - **particles**: Define as particulas exibidas.
    - Exemplo: `/windcharger configure particles FLAME`
  - **count-particles**: Define o número de partículas a serem exibidas
    - . Exemplo: `/windcharger configure count-particles 10`
   

## Configurações

As configurações do plugin são armazenadas no arquivo de configuração `config.yml` e podem ser ajustadas manualmente ou via comando.

- **wind-power**: Define a força do vento. (default: `0.5`)
- **wind-particles**: Define o tipo de partículas a serem exibidas. (default: `FALSE`)
- **wind-count-particles**: Define a quantidade de partículas a serem exibidas. (default: `10`)
- **wind-speed**: Define a velocidade do projétil de vento. (default: `0.5`)

### Exemplo de Configuração

```yaml
wind-power: 0.5
wind-particles: FLAME
wind-count-particles: 10
wind-speed: 0.5
```

## Eventos

### `ProjectileLaunchEvent`

- **Descrição**: Manipula o lançamento do Wind Charge, ajustando sua velocidade com base na configuração.

### `EntityExplodeEvent`

- **Descrição**: Manipula a explosão do Wind Charge, aplicando força de vento e exibindo partículas. Ajusta a física dos jogadores e entidades próximas `knockvback`.

### `EntityDamageEvent`

- **Descrição**: Ajusta o dano de queda baseado na distância de queda do jogador.


## Contato

Para dúvidas, entre em contato:
- **Nome**: Paulo Holanda
- **Email**: [paulorfholanda@gmail.com](mailto:paulorfholanda@gmail.com)
- **GitHub**: [PauloFH](https://github.com/PauloFH)
