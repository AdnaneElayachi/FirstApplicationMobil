package PokeService;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import model.PokyResponse;

public interface  PokeService {
    @GET("api/v2/pokemon/{id}")
    public Call<PokyResponse> searchPoky(@Path("id")int id);

    Call<PokemonList> getPokemons();
}
