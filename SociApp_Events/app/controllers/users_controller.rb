class UsersController < ApplicationController
	#GET /users
	def index	
		@users=User.all
	end
	#GET /users/:id
	def show
		@user=User.find(params[:id])
	end
	#GET users/new  --> Busca una accion new en el controlador, va a permirit crear un nuevo usuario
	def new
		#No esta en la base de datos esta en MEMORIA
		@user=User.new
	end
	#POST /users
	def create
		@user=User.new(name:params[:user][:name],
			email:params[:user][:email],
			password:params[:user][:password])
	@user.save
	redirect_to @user
	end
end