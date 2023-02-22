package news.tersa.hub.endpoints

import news.tersa.hub.config.security.JwtTokenUtils
import news.tersa.hub.config.security.JwtUserDetailsService
import news.tersa.hub.endpoints.requesttypes.JwtRequest
import news.tersa.hub.endpoints.requesttypes.JwtResponse
import news.tersa.hub.endpoints.requesttypes.RegisterUserRequest
import news.tersa.hub.models.User
import org.apache.commons.validator.routines.EmailValidator
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@CrossOrigin
class AuthenticationController {

    companion object : Logging {
        const val PASSWORD_REGEX_PATTERN = "^(?=.*[A-Z])(?=.*\\d).{8,}\$"
    }

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var jwtTokenUtils: JwtTokenUtils

    @Autowired
    private lateinit var userDetailsService: JwtUserDetailsService

    @RequestMapping(value = ["/auth"], method = [RequestMethod.POST])
    @Throws(Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<JwtResponse> {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password))
        } catch (e: DisabledException) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "User has been disabled")
        } catch (e: BadCredentialsException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User has been disabled")
        }
        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val token = jwtTokenUtils.generateToken(userDetails)
        return ResponseEntity.ok(JwtResponse(token))
    }

    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    @Throws(Exception::class)
    fun registerUser(@RequestBody registerRequest: RegisterUserRequest): ResponseEntity<User> {
        // do not allow bad email or password
        if (!EmailValidator.getInstance().isValid(registerRequest.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration requires a valid e-mail address")
        }
        if (!Regex(PASSWORD_REGEX_PATTERN).matches(registerRequest.password)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Requires a password at least 8 characters long and contains at least one uppercase letter and one number")
        }
        val newUser: User?
        try {
            newUser =
                userDetailsService.save(registerRequest.username, registerRequest.password, registerRequest.email)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "A user with that email address already exists")
        }

        return ResponseEntity.ok(newUser)
    }

}