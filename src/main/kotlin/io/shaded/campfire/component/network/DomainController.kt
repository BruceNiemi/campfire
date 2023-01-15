package io.shaded.campfire.component.network

import io.shaded.campfire.network.Domain
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/networks/{networkId}/domains")
class DomainController {

  @GetMapping
  fun listDomains(@PathVariable networkId: Long) : Flux<Domain> =
    Flux.empty()
}
