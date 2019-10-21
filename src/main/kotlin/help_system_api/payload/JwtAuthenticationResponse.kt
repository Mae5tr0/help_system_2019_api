package help_system_api.payload

class JwtAuthenticationResponse(var accessToken: String?) {
    var tokenType = "Bearer"
}