class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :name, :null => false, :default => ""
      t.string :email
      t.string :password

      t.timestamps
     ## Token authenticatable
       	t.string   :confirmation_token
		    t.datetime :confirmed_at
		    t.datetime :confirmation_sent_at
		    t.string   :authentication_token
	      add_index  :users, :authentication_token, :unique => true


    end
  end
end
