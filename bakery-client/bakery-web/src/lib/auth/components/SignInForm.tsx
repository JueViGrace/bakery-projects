import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@components/ui/form';
import { toast } from 'sonner';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Button } from '@components/ui/button';
import { Input } from '@components/ui/input';
import type { APIResponse } from '@/env';
import {
  type AuthResponse,
  type SignInRequest,
  type SignInFormSchema,
  signInFormSchema,
} from '@auth/types';

export default function SignInForm() {
  const form = useForm<SignInFormSchema>({
    resolver: zodResolver(signInFormSchema),
    defaultValues: {
      username: '',
      password: '',
    },
  });

  const onSubmit = async ({ username, password }: SignInFormSchema) => {
    try {
      const signInDto: SignInRequest = {
        username: username,
        password: password,
      };
      const req = await fetch('/api/auth/signin', {
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(signInDto),
        method: 'POST',
      });
      if (!req.ok) {
        const res: APIResponse<AuthResponse> = await req.json();
        toast.error(res.message);
        return;
      }

      toast.success('Welcome!');
      window.open('/', '_self');
    } catch (e) {
      console.error(e);
      toast.error('Failed to submit the form. Please try again.');
    }
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex flex-col items-center justify-center gap-6"
      >
        <FormField
          control={form.control}
          name="username"
          render={({ field }) => (
            <FormItem className="w-full">
              <FormLabel>Username</FormLabel>
              <FormControl>
                <Input
                  placeholder="Username or email..."
                  type="text"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem className="w-full">
              <FormLabel>Password</FormLabel>
              <FormControl>
                <Input type="password" placeholder="Password..." {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <Button type="submit" className="w-full cursor-pointer">
          Submit
        </Button>
      </form>
    </Form>
  );
}
