# Evidencia TP GitHub Actions CI/CD

## Repositorio

https://github.com/ICOMP-UNC/sof-eng-2026-skynet

## CI exitoso

Workflow `CI` ejecutado correctamente en Pull Request, con los jobs `Build` y `Test` finalizados exitosamente.

https://github.com/ICOMP-UNC/sof-eng-2026-skynet/actions/runs/27481633908

## Release workflow exitoso

Workflow `Release` ejecutado correctamente al subir el tag `v1.0.0`.

https://github.com/ICOMP-UNC/sof-eng-2026-skynet/actions/runs/27481741424

## Release publicado con JAR adjunto

Release `v1.0.0` publicado con el artefacto `.jar` adjunto.

https://github.com/ICOMP-UNC/sof-eng-2026-skynet/releases/tag/v1.0.0

## PR con CI fallando a propósito

Pull Request de evidencia con un test roto intencionalmente. El workflow `CI` falla en el job `Test`, demostrando que el pipeline bloquea cambios incorrectos.

Pull Request fallido:
https://github.com/ICOMP-UNC/sof-eng-2026-skynet/pull/42

Corrida fallida:
https://github.com/ICOMP-UNC/sof-eng-2026-skynet/actions/runs/27482060691