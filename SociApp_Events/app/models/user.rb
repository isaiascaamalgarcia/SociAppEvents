class User < ActiveRecord::Base
validates :name, presence: true, uniqueness:true
validates :email, presence: true
validates :password, presence: true
devise :database_authenticatable, :registerable, :recoverable, :rememberable, :trackable, :validatable, :token_authenticatable
before_save :ensure_authentication_token
attr_accessible :name, :email, :password, :password_confirmation, :remember_me
def skip_confirmation!
  self.confirmed_at = Time.now
end

end
